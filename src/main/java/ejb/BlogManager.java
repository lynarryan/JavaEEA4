package ejb;

import javax.ejb.Stateless;

@Stateless
public class BlogManager extends ManagerBeans{

    public BlogManager() {
        
    }
    public void outputValidBean() {
        System.out.println("Is a valid bean");
    }
}
