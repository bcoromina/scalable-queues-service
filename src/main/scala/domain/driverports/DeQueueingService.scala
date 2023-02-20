package domain.driverports

trait DeQueueingService[F[_]]{

  def start(): F[Unit]
  def stop(): F[Unit]

  def isStarted(): Boolean

}

