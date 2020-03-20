package com.phisuite.data

import com.phisuite.data.common.Options
import com.phisuite.data.entity.Entity
import com.phisuite.data.event.Event
import com.typesafe.scalalogging.Logger

object Main extends App {
  val grpcPort = sys.env.getOrElse("GRPC_PORT", "50051").toInt
  val eventPublisher = sys.env.getOrElse("EVENT_PUBLISHER", "event-publisher")
  val processExecutor = sys.env.getOrElse("PROCESS_EXECUTOR", "process-executor")
  val entityInspector = sys.env.getOrElse("ENTITY_INSPECTOR", "entity-inspector")
  val entityEditor = sys.env.getOrElse("ENTITY_EDITOR", "entity-editor")

  val logger = Logger("Main")
  val publisher = Publisher(eventPublisher, grpcPort)
  val processor = Processor(processExecutor, grpcPort)
  val inspector = Inspector(entityInspector, grpcPort)
  val editor = Editor(entityEditor, grpcPort)

  def buildEvent(a: Int, b: Int): Event = Event(name = s"Dummy-$a$b", version = s"$a.$b")
  def buildEntity(a: Int, b: Int): Entity = Entity(name = s"Dummy-$a$b", version = s"$a.$b")

  var i = 1
  while (true) {
    logger.info(s"Batch $i")
    for (j <- 1 until 10) {
      val event = buildEvent(i, j)
      val entity = buildEntity(i, j)
      publisher.publish(event)
      processor.process(event)
      inspector.List(Options(name = entity.name))
      editor.create(entity)
    }
    Thread.sleep(10000)
    i += 1
  }
}
