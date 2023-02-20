package drivenadapters


import weaver.SimpleIOSuite
import cats.effect.*
import domain.entities.{Job, JobId, QueueConfig}
import domain.entities.JobActions.Sleep

import scala.collection.mutable
import scala.concurrent.duration.*

//We will test InMemoryQueueStorage (Test) and RedisQueueStorage
object QueueStorageTest extends SimpleIOSuite{
  test("hello"){ _ =>
    val job = Job(JobId(), Sleep(1.second))
    val queue = QueueConfig("test-queue")
    for{
      state: Ref[IO, QueuesState] <- Ref[IO].of(QueuesState.empty)
      storage <- IO.delay( new InMemoryQueueStorage(state) )
      _ <- storage.enQueueJob(job, queue)
      isReady <- storage.isJobReady(job.id, queue)
    }yield expect.same(true, isReady)
  }
}
