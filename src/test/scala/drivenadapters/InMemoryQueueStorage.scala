package drivenadapters

import cats.{Applicative, Monad}
import cats.effect.{Ref, Sync}
import domain.drivenports.QueueStorage
import domain.entities.{Job, JobId, QueueConfig}
import cats.implicits.*
import scala.collection.mutable
import InMemoryQueueStorage.{QueueImpl, *}


case class QueuesState(
                        readyQueues: Map[String, scala.collection.immutable.Queue[Job]]

                      )

object QueuesState{
  def empty = QueuesState(Map.empty)
}

class InMemoryQueueStorage[F[_]: Monad](sharedState: Ref[F, QueuesState]) extends QueueStorage[F] {


  private def createQueueIfNoeExist(queueName: String, state: QueuesState) : QueuesState =
    state.readyQueues.get(queueName) match {
      case None => state.copy(readyQueues = state.readyQueues ++ Map(queueName -> scala.collection.immutable.Queue.empty[Job]))
    }

  private def putjobInQueue(job: Job, queueName: String, state: QueuesState): QueuesState = {
    val withQueue = createQueueIfNoeExist(queueName, state)
    val newReadyQueues = withQueue.readyQueues.get(queueName).get.appended(job)
    withQueue.copy(readyQueues = withQueue.readyQueues ++ Map(queueName -> newReadyQueues))
  }


  override def enQueueJob(job: Job, queue: QueueConfig): F[Unit] = sharedState.update(state => putjobInQueue(job, queue.name, state))


  override def reserveNextJob(queue: QueueConfig): F[Option[Job]] = ???

  override def markJobAsSuccess(job: Job): F[Unit] = ???

  override def markJobAsError(job: Job): F[Unit] = ???

  override def isJobReady(jobId: JobId, queue: QueueConfig): F[Boolean] = {
    Applicative[F].pure(true)
  }

}

object InMemoryQueueStorage{

  type QueueImpl = scala.collection.immutable.Queue[Job]
}