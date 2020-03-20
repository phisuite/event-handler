package com.phisuite.data

import com.phisuite.data.event.{Event, EventAPIGrpc}
import com.typesafe.scalalogging.Logger
import io.grpc.ManagedChannelBuilder

import scala.concurrent.Future

object Publisher {
  def apply(host: String, port: Int): Publisher = {
    new Publisher(ManagedChannelBuilder.forAddress(host, port).usePlaintext())
  }
}

class Publisher private (channel: ManagedChannelBuilder[_]) {
  private val logger = Logger("Publisher")
  private val stub = EventAPIGrpc.stub(channel.build())

  def publish(event: Event): Future[Event] = {
    logger.info(s"Publish: $event")
    stub.publish(event)
  }
}
