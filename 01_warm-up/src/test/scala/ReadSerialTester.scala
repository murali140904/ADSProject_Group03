// ADS I Class Project
// Chisel Introduction

package readserial

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

/**
  * Read serial tester
  */
class ReadSerialTester extends AnyFlatSpec with ChiselScalatestTester {

  "ReadSerial" should "work" in {

    test(new ReadSerial).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>

      // =====================================================
      // Test Case 1 : Idle line
      // =====================================================

      dut.io.rxd.poke(true.B)
      dut.clock.step(3)

      dut.io.valid.expect(false.B)

      // =====================================================
      // Test Case 2 : Receive byte 10110011
      // =====================================================

      // Start bit
      dut.io.rxd.poke(false.B)
      dut.clock.step(1)

      // Send 8 bits (MSB first)
      val bits1 = Seq(
        true.B,   // 1
        false.B,  // 0
        true.B,   // 1
        true.B,   // 1
        false.B,  // 0
        false.B,  // 0
        true.B,   // 1
        true.B    // 1
      )

      for(bit <- bits1) {
        dut.io.rxd.poke(bit)
        dut.clock.step(1)
      }

      // Check outputs
      dut.io.valid.expect(true.B)
      dut.io.data.expect("b10110011".U)

      dut.clock.step(1)

      // =====================================================
      // Test Case 3 : Consecutive transmission
      // =====================================================

      // Start bit immediately after previous transmission
      dut.io.rxd.poke(false.B)
      dut.clock.step(1)

      // Send 11110000
      val bits2 = Seq(
        true.B,
        true.B,
        true.B,
        true.B,
        false.B,
        false.B,
        false.B,
        false.B
      )

      for(bit <- bits2) {
        dut.io.rxd.poke(bit)
        dut.clock.step(1)
      }

      // Check outputs
      dut.io.valid.expect(true.B)
      dut.io.data.expect("b11110000".U)

      dut.clock.step(1)

      // =====================================================
      // Test Case 4 : Return to idle
      // =====================================================

      dut.io.rxd.poke(true.B)
      dut.clock.step(5)

      dut.io.valid.expect(false.B)
    }
  }
}