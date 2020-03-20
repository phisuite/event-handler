package com.phisuite.data

import com.phisuite.data.common.Options
import com.phisuite.data.entity.{Entity, EntityAPIGrpc}
import com.typesafe.scalalogging.Logger
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver

object Inspector {
  def apply(host: String, port: Int): Inspector = {
    new Inspector(ManagedChannelBuilder.forAddress(host, port).usePlaintext())
  }
}

class Inspector private (channel: ManagedChannelBuilder[_]) {
  private val logger = Logger("Inspector")
  private val stub = EntityAPIGrpc.stub(channel.build())

  def List(options: Options): Unit = {
    logger.info(s"List entities: $options")
    stub.list(options, new StreamObserver[Entity] {
      override def onNext(value: Entity): Unit = logger.info(s"Success: $value")

      override def onError(t: Throwable): Unit = logger.error(s"Error: $t")

      override def onCompleted(): Unit = logger.info("Done")
    })
  }
}
