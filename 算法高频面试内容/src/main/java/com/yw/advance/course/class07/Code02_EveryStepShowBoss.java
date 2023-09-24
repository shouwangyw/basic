package com.yw.advance.course.class07;

import java.util.*;

import static com.yw.util.CommonUtils.printArray;

/**
 * @author yangwei
 */
public class Code02_EveryStepShowBoss {

    // 用户类
private static class User implements Comparable<User> {
    int id;         // 用户ID
    int buyCnt;     // 购买数量
    int time;       // 最后更新时间(进入得奖区或候选区的时间)
    public User(int id, int time) {
        this.id = id;
        this.time = time;
    }
    public User(int id, int buyCnt, int time) {
        this.id = id;
        this.buyCnt = buyCnt;
        this.time = time;
    }

    @Override
    public int compareTo(User o) {
        return o == null ? 0 : this.id - o.id;
    }
}
    // 得奖Service类
    public static class AwardService {
        private Map<Integer, User> userMap;     // 用户索引表
        private HeapPlus<User> awardArea;       // 得奖区
        private HeapPlus<User> candidateArea;   // 候选区
        private final int limit;                // 限制得奖名额
        public AwardService(int limit) {
            this.userMap = new HashMap<>();
            // 得奖区，优先按购买数量从小到大排序(便于从得奖区找到最小值移出)
            // 其次按进入时间，即购买商品数量相同，按时间先后排序，先进入得奖区的则先出去进入候选区或移除
            this.awardArea = new HeapPlus<>(((o1, o2) -> o1.buyCnt != o2.buyCnt ? o1.buyCnt - o2.buyCnt : o1.time - o2.time));
            // 候选区，优先按购买数量从大到小排序(便于从候选区找到最大值移出，并进入到得奖区)
            // 其次按进入时间，即购买商品数量相同，按时间先后排序，先进入候选区的则先出去进入得奖区
            this.candidateArea = new HeapPlus<>((o1, o2) -> o1.buyCnt != o2.buyCnt ? o2.buyCnt - o1.buyCnt : o1.time - o2.time);
            this.limit = limit;
        }
        // 在(time)时间,用户(id),买(flag=true)或退(flag=false)商品
        public void handle(int time, int id, boolean flag) {
            // ============= 1. 维护用户索引表 =============
            User user = userMap.get(id);    // 获取当前用户
            // 若用户不存在，且是退货，则忽略
            if (user == null && !flag) return;
            // 到这里，要么购买商品了，要么用户存在但退货了
            if (user == null) {
                user = new User(id, 1, time);
                userMap.put(id, user);
            } else {
                if (flag) user.buyCnt++;
                else user.buyCnt--;
            }
            // user.buyCnt-- 如果减到0，则从`用户索引表`移除
            if (user.buyCnt == 0) userMap.remove(id);
            // ============= 2. 维护得奖区和候选区 =============
            // 当前用户: 1.在得奖区, 2.在候选区, 3.都不在
            // remove、reBalance方法堆实现时，若不存在，则忽略，所以是安全的
            if (awardArea.exist(user)) {
                if (user.buyCnt == 0) awardArea.remove(user);
                else awardArea.reBalance(user);
            } else if (candidateArea.exist(user)) {
                if (user.buyCnt == 0) candidateArea.remove(user);
                else candidateArea.reBalance(user);
            } else {
                // 都不在，说明是buyCnt从0到1，要么进得奖区；要么得奖区满了，进候选区
                user.time = time;
                if (awardArea.size() < limit) awardArea.add(user);
                else candidateArea.add(user);
            }
            // ============= 3. 调整得奖区、候选区 =============
            // 此时得奖区可能有空余名额，可以挑选候选区用户进入得奖区了；候选区堆顶的用户可能因为购买，已经有资格替换得奖区末尾用户了
            if (candidateArea.isEmpty()) return;
            if (awardArea.size() < limit) {
                // 得奖区还有名额，且候选区有用户，则将候选区第一名加入到得奖区
                User u = candidateArea.poll();
                u.time = time;
                awardArea.add(u);
                return;
            }
            // 得奖区满了，那就拿 候选区第一名 与 得奖区最后一名 PK 购买数量
            if (candidateArea.peek().buyCnt <= awardArea.peek().buyCnt) return;
            User oldAward = awardArea.poll();
            User newAward = candidateArea.poll();
            oldAward.time = time;
            newAward.time = time;
            awardArea.add(newAward);
            candidateArea.add(oldAward);
        }
        // 获取得奖区所有用户
        public List<Integer> getAwardUserIds() {
            List<User> users = awardArea.getItems();
            List<Integer> userIds = new ArrayList<>();
            users.forEach(u -> userIds.add(u.id));
            return userIds;
        }
    }

    private static class Customer {
        int id;
        int buy;
        int enterTime;

        public Customer(int v, int b, int o) {
            id = v;
            buy = b;
            enterTime = 0;
        }
    }

    public static class WhosYourDaddy {
        private HashMap<Integer, Customer> customers;
        private HeapGreater<Customer> candHeap;
        private HeapGreater<Customer> daddyHeap;
        private final int daddyLimit;

        public WhosYourDaddy(int limit) {
            customers = new HashMap<>();
            candHeap = new HeapGreater<>((o1, o2) -> o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime));
            daddyHeap = new HeapGreater<>((o1, o2) -> o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime));
            daddyLimit = limit;
        }

        // 当前处理i号事件，arr[i] -> id,  buyOrRefund
        public void operate(int time, int id, boolean buyOrRefund) {
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer c = customers.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                customers.remove(id);
            }
            if (!candHeap.contains(c) && !daddyHeap.contains(c)) {
                if (daddyHeap.size() < daddyLimit) {
                    c.enterTime = time;
                    daddyHeap.push(c);
                } else {
                    c.enterTime = time;
                    candHeap.push(c);
                }
            } else if (candHeap.contains(c)) {
                if (c.buy == 0) {
                    candHeap.remove(c);
                } else {
                    candHeap.resign(c);
                }
            } else {
                if (c.buy == 0) {
                    daddyHeap.remove(c);
                } else {
                    daddyHeap.resign(c);
                }
            }
            daddyMove(time);
        }

        public List<Integer> getDaddies() {
            List<Customer> customers = daddyHeap.getAllElements();
            List<Integer> ans = new ArrayList<>();
            for (Customer c : customers) {
                ans.add(c.id);
            }
            return ans;
        }

        private void daddyMove(int time) {
            if (candHeap.isEmpty()) {
                return;
            }
            if (daddyHeap.size() < daddyLimit) {
                Customer p = candHeap.pop();
                p.enterTime = time;
                daddyHeap.push(p);
            } else {
                if (candHeap.peek().buy > daddyHeap.peek().buy) {
                    Customer oldDaddy = daddyHeap.pop();
                    Customer newDaddy = candHeap.pop();
                    oldDaddy.enterTime = time;
                    newDaddy.enterTime = time;
                    daddyHeap.push(newDaddy);
                    candHeap.push(oldDaddy);
                }
            }
        }

    }

    // 干完所有的事，模拟，不优化
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(daddy));
                continue;
            }
            // 没有发生：用户购买数为0并且又退货了
            // 用户之前购买数是0，此时买货事件
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            // 买、卖
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                map.remove(id);
            }
            // c
            // 下面做
            if (!cands.contains(c) && !daddy.contains(c)) {
                if (daddy.size() < k) {
                    c.enterTime = i;
                    daddy.add(c);
                } else {
                    c.enterTime = i;
                    cands.add(c);
                }
            }
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            cands.sort((o1, o2) -> o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime));
            daddy.sort((o1, o2) -> o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime));
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static void move(List<Customer> cands, List<Customer> daddy, int k, int time) {
        if (cands.isEmpty()) {
            return;
        }
        // 候选区不为空
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.enterTime = time;
            daddy.add(c);
            cands.remove(0);
        } else { // 等奖区满了，候选区有东西
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
    }

    public static void cleanZeroBuy(List<Customer> arr) {
        List<Customer> noZero = new ArrayList<>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }

    public static List<Integer> getCurAns(List<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy) {
            ans.add(c.id);
        }
        return ans;
    }

    // 为了测试
    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort(Comparator.comparingInt(a -> a));
            cur2.sort(Comparator.comparingInt(a -> a));
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return 每一时间点的得奖区用户
     */
    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
//        WhosYourDaddy whoDaddies = new WhosYourDaddy(k);
//        for (int i = 0; i < arr.length; i++) {
//            whoDaddies.operate(i, arr[i], op[i]);
//            ans.add(whoDaddies.getDaddies());
//        }
        AwardService service = new AwardService(k);
        for (int i = 0; i < arr.length; i++) {
            service.handle(i, arr[i], op[i]);
            ans.add(service.getAwardUserIds());
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {4, 4, 9, 9, 5, 2, 3};
//        boolean[] op = {false, true, true, true, false, true, true};
//        List<List<Integer>> ans = topK(arr, op, 3);

//        int[] arr = {6, 1, 2, 6, 1};
//        boolean[] op = {false, true, false, false, false};
//        List<List<Integer>> ans = topK(arr, op, 6);
//        System.out.println(ans);

//        int[] arr = {4, 1, 1, 4, 6, 3, 2, 7, 4};
//        boolean[] op = {true, true, true, false, false, false, true, false, true};
//        List<List<Integer>> ans = topK(arr, op, 1);
//        System.out.println(ans);

        int maxValue = 10;
        int maxLen = 10;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                printArray(arr);
                printArray(op);
                System.out.println(k);

                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
