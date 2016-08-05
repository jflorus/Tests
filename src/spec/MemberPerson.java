package spec;

import domain.MemberPersonDetails;
import domain.SalesPersonDetails;
import pages.PageStore;

/**
 * Created by jovana on 6/29/16.
 */
public class MemberPerson {
    PageStore pageStore;
    private boolean isLoggedIn;
    public MemberPersonDetails memberPersonDetails;
    private SalesPerson.App app;
    public enum App {GENERIC, POS, TAILORING }
    public enum OrderType {CUSTOM, READYTOWEAR}


    public MemberPerson(PageStore pageStore, MemberPersonDetails memberPersonDetails) {
        this.pageStore = pageStore;
        this.memberPersonDetails = memberPersonDetails;
    }
}
