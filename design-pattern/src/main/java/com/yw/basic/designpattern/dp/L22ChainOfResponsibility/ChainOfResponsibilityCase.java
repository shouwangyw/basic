package com.yw.basic.designpattern.dp.L22ChainOfResponsibility;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 职责链模式：解决OA系统采购审批
 *
 * @author yangwei
 */
public class ChainOfResponsibilityCase {
    public static void main(String[] args) {
        // 创建一个请求
        PurchaseRequest request = new PurchaseRequest(1, 31000, 1);
        // 创建各个级别的审批人
        DepartmentApprover departmentApprover = new DepartmentApprover();
        CollegeApprover collegeApprover = new CollegeApprover();
        ViceShcoolMasterApprover viceShcoolMasterApprover = new ViceShcoolMasterApprover();
        ShcoolMasterApprover shcoolMasterApprover = new ShcoolMasterApprover();
        // 将各个审批级别的下一个审批人设置好，形成环
        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(viceShcoolMasterApprover);
        viceShcoolMasterApprover.setApprover(shcoolMasterApprover);
        shcoolMasterApprover.setApprover(departmentApprover);

        System.out.println("----开始从教学主任处理----");
        departmentApprover.processRequest(request);
        System.out.println("----开始从副校长处理----");
        viceShcoolMasterApprover.processRequest(request);
    }
}

/**
 * 请求类
 */
@Data
@AllArgsConstructor
class PurchaseRequest {
    private int type;
    private float price;
    private int id;
}

/**
 * 审批人
 */
abstract class Approver {
    /**
     * 下一个处理者
     */
    Approver approver;
    String name;
    Approver(String name) {
        this.name = name;
    }
    public void setApprover(Approver approver) {
        this.approver = approver;
    }
    abstract void processRequest(PurchaseRequest request);
}

/**
 * 下面是具体的审批人
 */
class DepartmentApprover extends Approver {
    DepartmentApprover() {
        super("教学主任");
    }
    @Override
    void processRequest(PurchaseRequest request) {
        if (request.getPrice() <= 5000) {
            System.out.println("采购单 id = " + request.getId() + " 被 " + this.name + " 审批");
        } else {
            System.out.println(this.name + " 审批不了");
            approver.processRequest(request);
        }
    }
}

class CollegeApprover extends Approver {
    CollegeApprover() {
        super("院长");
    }
    @Override
    void processRequest(PurchaseRequest request) {
        if (request.getPrice() > 5000 && request.getPrice() <= 10000) {
            System.out.println("采购单 id = " + request.getId() + " 被 " + this.name + " 审批");
        } else {
            System.out.println(this.name + " 审批不了");
            approver.processRequest(request);
        }
    }
}

class ViceShcoolMasterApprover extends Approver {
    ViceShcoolMasterApprover() {
        super("副校长");
    }
    @Override
    void processRequest(PurchaseRequest request) {
        if (request.getPrice() > 10000 && request.getPrice() <= 30000) {
            System.out.println("采购单 id = " + request.getId() + " 被 " + this.name + " 审批");
        } else {
            System.out.println(this.name + " 审批不了");
            approver.processRequest(request);
        }
    }
}

class ShcoolMasterApprover extends Approver {
    ShcoolMasterApprover() {
        super("校长");
    }
    @Override
    void processRequest(PurchaseRequest request) {
        if (request.getPrice() > 30000) {
            System.out.println("采购单 id = " + request.getId() + " 被 " + this.name + " 审批");
        } else {
            System.out.println(this.name + " 审批不了");
            approver.processRequest(request);
        }
    }
}
