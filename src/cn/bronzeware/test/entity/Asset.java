package cn.bronzeware.test.entity;

import java.util.Date;

public class Asset {
    protected Integer id;

    protected String sob;

    protected String code;

    protected String name;

    protected String type;

    protected String model;

    protected Date purchasetime;

    protected Double value;

    protected String user;

    protected String usedepartment;

    protected String keeper;

    protected String keepdepartment;

    protected String usestate;

    protected String certificate;

    protected String state;

    protected Boolean isbegin;

    protected String ghostyear;

    protected Date startusetime;

    protected Date makecardtime;

    protected String remark;

    protected String number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSob() {
        return sob;
    }

    public void setSob(String sob) {
        this.sob = sob == null ? null : sob.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public Date getPurchasetime() {
        return purchasetime;
    }

    public void setPurchasetime(Date purchasetime) {
        this.purchasetime = purchasetime;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public String getUsedepartment() {
        return usedepartment;
    }

    public void setUsedepartment(String usedepartment) {
        this.usedepartment = usedepartment == null ? null : usedepartment.trim();
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper == null ? null : keeper.trim();
    }

    public String getKeepdepartment() {
        return keepdepartment;
    }

    public void setKeepdepartment(String keepdepartment) {
        this.keepdepartment = keepdepartment == null ? null : keepdepartment.trim();
    }

    public String getUsestate() {
        return usestate;
    }

    public void setUsestate(String usestate) {
        this.usestate = usestate == null ? null : usestate.trim();
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate == null ? null : certificate.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Boolean getIsbegin() {
        return isbegin;
    }

    public void setIsbegin(Boolean isbegin) {
        this.isbegin = isbegin;
    }

    public String getGhostyear() {
        return ghostyear;
    }

    public void setGhostyear(String ghostyear) {
        this.ghostyear = ghostyear == null ? null : ghostyear.trim();
    }

    public Date getStartusetime() {
        return startusetime;
    }

    public void setStartusetime(Date startusetime) {
        this.startusetime = startusetime;
    }

    public Date getMakecardtime() {
        return makecardtime;
    }

    public void setMakecardtime(Date makecardtime) {
        this.makecardtime = makecardtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }
}