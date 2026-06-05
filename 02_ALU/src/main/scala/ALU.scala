// ADS I Class Project
// Assignment 02: Arithmetic Logic Unit and UVM Testbench

package Assignment02 //groups all the files in assignment 2


import chisel3._
import chisel3.util._ // importing libraries
import chisel3.experimental.ChiselEnum //  enumirating rather than assigning op codes to readable op codes

object ALUOp extends ChiselEnum { // creates all AlU operations
  val ADD, SUB, AND, OR, XOR,  
      SLL, SRL, SRA,
      SLT, SLTU,
      PASSB = Value
}

class ALU extends Module {    // creates hardware module

  val io = IO(new Bundle { // to define all ports

    val operandA = Input(UInt(32.W))

    val operandB = Input(UInt(32.W))

    val operation = Input(ALUOp())

    val aluResult = Output(UInt(32.W))

  })

  io.aluResult := 0.U //if no operation matches ouput=0

  switch(io.operation) { // swuitch case statement 

    is(ALUOp.ADD) {
      io.aluResult := io.operandA + io.operandB
    }

    is(ALUOp.SUB) {
      io.aluResult := io.operandA - io.operandB
    }

    is(ALUOp.AND) {
      io.aluResult := io.operandA & io.operandB
    }

    is(ALUOp.OR) {
      io.aluResult := io.operandA | io.operandB
    }

    is(ALUOp.XOR) {
      io.aluResult := io.operandA ^ io.operandB
    }

    is(ALUOp.SLL) {
      io.aluResult := io.operandA << io.operandB(4,0)
    }

    is(ALUOp.SRL) {
      io.aluResult := io.operandA >> io.operandB(4,0)
    }

    is(ALUOp.SRA) {
      io.aluResult := (io.operandA.asSInt >> io.operandB(4,0)).asUInt // assInt converts unsigned to signed values
    }

    is(ALUOp.SLT) {
      io.aluResult := (io.operandA.asSInt < io.operandB.asSInt).asUInt
    }

    is(ALUOp.SLTU) {
      io.aluResult := (io.operandA < io.operandB).asUInt
    }

    is(ALUOp.PASSB) {
      io.aluResult := io.operandB
    }
  }
}