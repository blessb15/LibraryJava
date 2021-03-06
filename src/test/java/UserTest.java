import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class UserTest{

  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
  }

  @After
  public void teardown(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM users *";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void User_UserInstantiates_true(){
    User newUser = new User("Bill", "508-134-4325");
    assertTrue(newUser instanceof User);
  }

  @Test
  public void User_UserInstantiatesWithName_Bill(){
    User newUser = new User("Bill", "508-134-4325");
    assertEquals("Bill", newUser.getName());
  }

  @Test
  public void User_UserisSavedtoDataBase_true(){
    User newUser = new User("Bill", "508-134-4325");
    newUser.save();
    assertTrue(User.all().size() == 1);
  }

  @Test
  public void User_UserObjectsEqual() {
    User firstUser = new User("Bill", "508-134-4325");
    User secondUser = new User("Bill", "508-134-4325");
    assertTrue(firstUser.equals(secondUser));
  }

  @Test
  public void User_FindUserData() {
    User newUser = new User("Bill", "512-124-1234");
    newUser.save();
    assertTrue(newUser.equals(User.find(newUser.getId())));
  }

  @Test
  public void User_DeletesUserFromDataBase() {
    User newUser = new User("Bill", "512-124-1234");
    newUser.save();
    newUser.delete();
    assertEquals(null, User.find(newUser.getId()));
  }

  @Test
  public void User_ListsCheckedOutBooks_true(){
    Book newBook = new Book("Bills book", "Bob", 12);
    newBook.save();
    User newUser = new User("Bill", "912-141-1411");
    newUser.save();
    newBook.checkout(newUser);
    System.out.println(newBook.getId());
    System.out.println(newUser.getId());
    System.out.println(newUser.checkedOutBooks();
    assertTrue(1 == newUser.checkedOutBooks().size());

  }
}
