package edu.nyu.acm.accession

import java.io.{ File, FileWriter } 
import java.util.UUID
import scala.io.Source

object Main2 extends App {
  
  var map: Map[String, Tuple3[String, Int, Long]] = Map()

  Source.fromFile(args(0)).getLines.foreach { line =>
  	val fields = line.split("\t")
  	val puid = fields(0)
  	val mime = fields(1)
  	val size = fields(3).toLong
  	if(map.contains(puid)) {
  		val format = map(puid)
  		val count = format._2 + 1
  		val newSize = format._3 + size
  		map = map + (puid -> new Tuple3(mime, count, newSize))
  	}
  	else {
  		map = map + (puid -> new Tuple3(mime, 1, size))
  	}
  }

  val writer = new FileWriter(new File("output.tsv"))
  map.foreach { m =>
  	writer.append(m._1 + "\t" + m._2._1 + "\t" + m._2._2 + "\t" + m._2._3 + "\n")
  	writer.flush
  }
  writer.close
}