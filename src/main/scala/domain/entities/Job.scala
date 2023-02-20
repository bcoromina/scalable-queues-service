package domain.entities

import scala.concurrent.duration.Duration


case class Job(id: JobId, action: JobActions)

case class JobId private (id: java.util.UUID)

object JobId{
  def apply() =  new JobId(java.util.UUID.randomUUID())
}
enum JobState:
  case ready
  case reserved
  case processed
  case error

enum JobActions:
  case Sleep(d: Duration)


