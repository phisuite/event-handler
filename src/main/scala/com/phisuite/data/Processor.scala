package com.phisuite.data

import com.phisuite.data.event.Event
import com.phisuite.data.process.{ProcessAPIGrpc, ProcessRequest, ProcessResponse}
import com.typesafe.scalalogging.Logger
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver

object Processor {
  def apply(host: String, port: Int): Processor = {
    new Processor(ManagedChannelBuilder.forAddress(host, port).usePlaintext())
  }
}

class Processor private (channel: ManagedChannelBuilder[_]) {
  private val logger = Logger("Processor")
  private val stub = ProcessAPIGrpc.stub(channel.build())

  def process(event: Event): Unit = {
    val stream = stub.execute(new StreamObserver[ProcessResponse] {
      override def onNext(value: ProcessResponse): Unit = logger.info(s"Success: $value")

      override def onError(t: Throwable): Unit = logger.error(s"Error: $t")

      override def onCompleted(): Unit = logger.info("Done")
    })
    for (i <- 1 to 10) {
      val request = ProcessRequest(name = s"${event.name}$i", version = s"${event.version}.$i")
      logger.info(s"Execute: $request")
      stream.onNext(request)
    }
    stream.onCompleted()
  }
}
