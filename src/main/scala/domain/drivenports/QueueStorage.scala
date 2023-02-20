package domain.drivenports

import domain.entities.{Job, JobId, QueueConfig}

trait QueueStorage[F[_]] {
  def enQueueJob(job: Job, queue: QueueConfig): F[Unit]

  def reserveNextJob(queue: QueueConfig): F[Option[Job]]

  def markJobAsSuccess(job: Job): F[Unit]

  def markJobAsError(job: Job): F[Unit]

  def isJobReady(jobId: JobId, queue: QueueConfig): F[Boolean]

}
