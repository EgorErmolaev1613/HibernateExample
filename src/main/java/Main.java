import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

//Реализуйте несколько методов для работы с базой данных. Все методы должны быть реализованы
// по примеру концепции DAO класса. Добавьте следующее:
//
//        Метод для добавления новой записи в таблицу;
//        Метод для редактиирования записи;
//        Метод для нахождения пользователя по его имени «username»;
//        Метод для поиска всех записей, где возраст в диапазоне от 10 (включительно) до 35 (не включительно);
//        Метод для удаления
public class Main {

   private static final EntityManagerFactory MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    public static void main(String[] args) {
    addUser("Egor Ermolaev",30);
    getUsersFrom10To35();
    editUser(1,"Vasya",23);
        getUsersFrom10To35();

//       deleteUser(1);

    }

   public static void addUser (String username,int age) {
        EntityManager entityManager = MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = null;

        User user = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            user = new User (username,age);
            entityManager.persist(user);
            entityTransaction.commit();
        }catch (Exception ex) {
            if (entityTransaction !=null) {
                entityTransaction.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            entityManager.close();
        }
   }

   public static void editUser (int id,String newUserName, int newAge) {
        EntityManager entityManager = MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = null;

        User user = null;

       try {
           entityTransaction = entityManager.getTransaction();
           entityTransaction.begin();

           user = entityManager.find(User.class,id);
           user.setName(newUserName);
           user.setAge(newAge);
           entityManager.persist(user);
           entityTransaction.commit();
       }catch (Exception ex) {
           if (entityTransaction !=null) {
               entityTransaction.rollback();
           }
           ex.printStackTrace();
       }
       finally {
           entityManager.close();
       }
   }

   public static void getUser (String name) {
       EntityManager entityManager = MANAGER_FACTORY.createEntityManager();
       String query = "SELECT u FROM User WHERE username = :i";
       TypedQuery<User> tq = entityManager.createQuery(query, User.class);
       tq.setParameter("i",name);
       User user = null;

       try {
           user = tq.getSingleResult();
           System.out.println("Name of User is " + user.getName() + ", age of User is " + user.getAge());

       }catch (Exception ex) {
           ex.printStackTrace();
       }
       finally {
           entityManager.close();
       }
   }
    public static void getUsersFrom10To35 () {
        EntityManager entityManager = MANAGER_FACTORY.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.age >= 10 AND u.age <35";
        TypedQuery<User> tq = entityManager.createQuery(query, User.class);
        List<User> list;

        try {
            list = tq.getResultList();
            int i = 1;
            for (User user : list)
            System.out.println("List of users over 10 and under 35: \n" +
                    " -------------------------------------------- \n" +
                   i +  ". Name of User is " + user.getName() + ", age of User is " + user.getAge());
            i++;

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }

    public static void deleteUser (int id) {
        EntityManager entityManager = MANAGER_FACTORY.createEntityManager();

        EntityTransaction entityTransaction = null;

        User item = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            item = entityManager.find(User.class,id);
            entityManager.remove(item);
            entityTransaction.commit();

        }catch (Exception e){
            if (entityTransaction!=null){
                entityTransaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
            entityManager.close();

        }

    }

}
