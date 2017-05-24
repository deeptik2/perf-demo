package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utililty.MyFeeder

import scala.io.Source

object BlogApis {

  val usernameFeeder = MyFeeder()
  val blogcontent = Source.fromFile("src/test/resources/blog/blog_content.txt").mkString


  val createUserApi = http("1. Create User API")
    .post("/users/signup")
    .formParam("username", "${username}")
    .formParam("name", "${username}")
    .formParam("password", "tester123")
    .check(status is 201)
    .check(jsonPath("$.username").saveAs("username"))

  val logInApi = http("2. Log in API")
    .post("/users/login")
    .formParam("username", "${username}")
    .formParam("password", "tester123")
    .check(status is 200)

  val createBlog = http("3. Create Blog")
    .post("/blog/new")
    .formParam("author", "${username}")
    .formParam("title", "This Is Dummy")
    .formParam("body", blogcontent)
    .check(status is 201)
    .check(jsonPath("$.id").saveAs("postId"))

  val getAllBlogs = http("4. Get All Blogs")
    .get("/blog")
    .check(status is 200)

  val getAllBlogsByUserName = http("5. Get Blog By username")
    .get("/blog/${username}")
    .queryParam("page", "1")
    .queryParam("limit", "1")
    .check(status is 200)

  val getBlogByUsernameAndPostId = http("6. Get Blog By username and post Id")
    .get("/blog/${username}/${postId}")
    .queryParam("page", "1")
    .queryParam("limit", "1")
    .check(status is 200)

  val updateBlog = http("7. Update Blog")
    .post("/blog/${username}/${postId}").notSilent
    .formParam("title", "This Is Updated").notSilent
    .formParam("body", "This is updated blog. yay! I destroyed the whole content. That's why we need authentication!")
    .check(status is 201)

  val deleteBlog = http("8. Delete Blog")
    .post("/blog/${username}/${postId}/delete")
    .check(status is 201)


  val blogEndToEndFlow = scenario("End to End Flow for Users and Blog")
    .feed(usernameFeeder)
    .exec(createUserApi)
    .exec(logInApi)
    .exec(createBlog)
    .exec(getAllBlogs)
    .exec(getAllBlogsByUserName)
    .exec(getBlogByUsernameAndPostId)
    .exec(updateBlog)
    .exec(deleteBlog)

}

