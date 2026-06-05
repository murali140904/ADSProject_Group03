// ADS I Class Project
// Assignment 02: Arithmetic Logic Unit and UVM Testbench
//
// Chair of Electronic Design Automation, RPTU University Kaiserslautern-Landau
// File created on 09/21/2025 by Tharindu Samarakoon (gug75kex@rptu.de)
// File updated on 10/31/2025 by Tobias Jauch (tobias.jauch@rptu.de)

`include "uvm_macros.svh"
import uvm_pkg::*;
import alu_tb_config_pkg::*;

module alu_tb();

timeunit 1ns; //defines simulation time unit
timeprecision 1ns; //defines simulation accuracy(smallest delay)

logic clk;  // declares clock signal
logic rst = 1'b0;  // declares reset signal output 0(inactive)

initial begin 
    clk = 0; // clock starts at 0
    forever begin //creates infinite loop and clock continues forever
        #(CLK_PERIOD/2); //half clock period
        clk = ~clk; //generates clock
    end
end

alu_if alu_if(clk); //creates interface instance

ALU dut( //interface bundles dut signals
    // .clock(clk),
    // .reset(rst),
    .io_operation(alu_if.operation),
    .io_operandA(alu_if.operandA),
    .io_operandB(alu_if.operandB),
    .io_aluResult(alu_if.aluResult) //dut output back to interface
);

initial begin // second inital block to uvm simulation
    uvm_config_db #(virtual alu_if)::set(null, "uvm_test_top", "alu_if", alu_if); //stores interface in db undername alu_if
    run_test("alu_test"); //starts uvm simulation
end

// dump waveform
initial begin // used for waveform generation
    $dumpfile("alu_dump.vcd"); // creates waveform file
    $dumpvars; // records all signal changes.
end

endmodule