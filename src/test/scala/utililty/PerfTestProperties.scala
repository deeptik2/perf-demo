package utililty

import scala.util.Properties
import scala.concurrent.duration._

object PerfTestProperties {

  val blogBaseUrl = Properties.envOrElse("BASE_URL", "http://localhost:9000")

  val testRunDuration = Properties.envOrElse("TEST_CYCYLE_DURATION", "1").toInt minutes

  val thresholdResponseTimeInMillis = scala.util.Properties.envOrElse("THRESHOLD_RESPONSE_TIME_IN_MILLISECONDS", "10")

  val numberOfConcurrentUsers = Properties.envOrElse("LOADTEST_CONCURRENT_USERS", "10").toInt

  val allowedPercentageFailure = Properties.envOrElse("ALLOWED_PERCENTAGE_FAILURE", "1").toInt
}
