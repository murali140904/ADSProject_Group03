// ADS I Class Project
// Assignment 02: Arithmetic Logic Unit and UVM Testbench

`include "uvm_macros.svh"
import uvm_pkg::*;
import alu_tb_config_pkg::*;

class alu_seq_item extends uvm_sequence_item; //represents 1ALU Transactions 

    rand logic [DATA_WIDTH-1:0] operandA; //generates random values for many combinations and corner cases
    rand logic [DATA_WIDTH-1:0] operandB;
    rand ALUOp operation;

    logic [DATA_WIDTH-1:0] aluResult; // source output

    `uvm_object_utils_begin(alu_seq_item) //registers class with uvm factory creates objects 
        `uvm_field_int(operandA, UVM_ALL_ON)
        `uvm_field_int(operandB, UVM_ALL_ON)
        `uvm_field_enum(ALUOp, operation, UVM_ALL_ON)
        `uvm_field_int(aluResult, UVM_ALL_ON)
    `uvm_object_utils_end

    constraint valid_operation {  // restricts randomization allows only valid operations
        operation inside {
            ADD,
            SUB,
            AND,
            OR,
            XOR,
            SLL,
            SRL,
            SRA,
            SLT,
            SLTU,
            PASSB
        };
    }

    virtual function string convert2str(); //uses for debugging prints Transactions
        return $sformatf(
            "operandA: 0x%0x, operandB: 0x%0x, operation: %0p, aluResult: 0x%0x",
            operandA,
            operandB,
            operation,
            aluResult
        );
    endfunction

    function new(string name = "alu_seq_item");
        super.new(name);
    endfunction

endclass