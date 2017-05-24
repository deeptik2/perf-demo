package simulation

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scenarios.BlogApis
import utililty.PerfTestProperties._
import scala.concurrent.duration._

class BlogsPerfLoadTest extends Simulation{

  val httpConf = http.baseURL(blogBaseUrl)

  val BlogCreateAndDelete = List(
    BlogApis.blogEndToEndFlow.inject(constantUsersPerSec(numberOfConcurrentUsers) during testRunDuration))

  setUp(BlogCreateAndDelete)
    .protocols(httpConf)
    .maxDuration(testRunDuration)
    .assertions(global.failedRequests.percent.lessThan(allowedPercentageFailure))
    .assertions(global.responseTime.percentile2.lessThan(thresholdResponseTimeInMillis.toInt))

}
