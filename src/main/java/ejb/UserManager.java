package ejb;

import javax.ejb.Stateless;
import models.BlogUser;

@Stateless
public class UserManager extends ManagerBeans {

    public BlogUser find(int id) {
        BlogUser b = new BlogUser();
        b.setFirstName("TestName");
        return b;
    }
}
