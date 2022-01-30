package jw.lab4.checkers;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class UserSave extends User{

  HibernateTemplate template;

  jw.lab4.checkers.Database.Game game;

  public UserSave(){
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    this.game=(jw.lab4.checkers.Database.Game)context.getBean("game");
    setTemplate((HibernateTemplate)context.getBean("template"));
  }

  public void setTemplate(HibernateTemplate template) {
    this.template = template;
  }
  

  @Override
  public void error(String str) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void move(MoveInstructions instr) {
    if(instr.state == MoveInstructions.STATE.NEXT) {
      template.saveOrUpdate(game);
    }else{
      game.update(instr);
    }else{
      game.load();
    }else{
      template.
      tx = session.beginTransaction();
      List employees = session.createQuery("FROM Employee").list(); 
      for (Iterator iterator = employees.iterator(); iterator.hasNext();){
        Employee employee = (Employee) iterator.next(); 
        System.out.print("First Name: " + employee.getFirstName()); 
        System.out.print("  Last Name: " + employee.getLastName()); 
        System.out.println("  Salary: " + employee.getSalary()); 
      }
      tx.commit();
    }
  }

  @Override
  public void start() {
    // TODO Auto-generated method stub
    
  }
  
}
