package com.phisuite.data

import com.phisuite.data.entity.{Entity, EntityWriteAPIGrpc}
import com.typesafe.scalalogging.Logger
import io.grpc.ManagedChannelBuilder

import scala.concurrent.Future

object Editor {
  def apply(host: String, port: Int): Editor = {
    new Editor(ManagedChannelBuilder.forAddress(host, port).usePlaintext())
  }
}

class Editor private (channel: ManagedChannelBuilder[_]) {
  private val logger = Logger("Editor")
  private val stub = EntityWriteAPIGrpc.stub(channel.build())

  def create(entity: Entity): Future[Entity] = {
    logger.info(s"Create: $entity")
    stub.create(entity)
  }
}
