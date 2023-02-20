package domain.driverports


import domain.entities.{Job, QueueConfig}
import domain.drivenports.QueueStorage


trait EnqueuingService[F[_]] {
  def enQueue(job: Job, queue: QueueConfig): F[Unit]
}


class EnqueuingServiceImpl[F[_]](storage: QueueStorage[F]) extends EnqueuingService[F]{
  override def enQueue(job: Job, queue: QueueConfig): F[Unit] = 
    storage.enQueueJob(job, queue) // could do something else like increment a prometheus counter
}