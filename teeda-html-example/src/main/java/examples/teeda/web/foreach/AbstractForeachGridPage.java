package examples.teeda.web.foreach;

import java.math.BigDecimal;

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public abstract class AbstractForeachGridPage {

    protected Integer    aaa;

    protected String     bbb;

    protected BigDecimal ccc;

    protected FooDto[]   fooItems;

    protected Integer    fooIndex;

    protected Integer    fooIndexSelect;

    protected int        editStatus;

    protected int        bbbEditStatus;

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
        this.aaa = aaa;
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bbb) {
        this.bbb = bbb;
    }

    public BigDecimal getCcc() {
        return ccc;
    }

    public void setCcc(BigDecimal ccc) {
        this.ccc = ccc;
    }

    public Integer getFooIndex() {
        return fooIndex;
    }

    public void setFooIndex(Integer fooIndex) {
        this.fooIndex = fooIndex;
    }

    public Integer getFooIndexSelect() {
        return fooIndexSelect;
    }

    public void setFooIndexSelect(Integer fooIndexSelect) {
        this.fooIndexSelect = fooIndexSelect;
    }

    public FooDto[] getFooItems() {
        return fooItems;
    }

    public void setFooItems(FooDto[] fooItems) {
        this.fooItems = fooItems;
    }

    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }

    public int getBbbEditStatus() {
        return bbbEditStatus;
    }

    public void setBbbEditStatus(int bbbEditStatus) {
        this.bbbEditStatus = bbbEditStatus;
    }

    public Boolean isEditStatusNormal() {
        return new Boolean((this.editStatus == PageUtil.EDIT_NOT_CHANGE));
    }

    public Boolean isEditStatusAdd() {
        return new Boolean(this.editStatus == PageUtil.EDIT_ADD);
    }

    public Boolean isEditStatusUpdate() {
        return new Boolean(this.editStatus == PageUtil.EDIT_UPDATE);
    }

    public Boolean isEditStatusDelete() {
        return new Boolean(this.editStatus == PageUtil.EDIT_DELETE);
    }
}
