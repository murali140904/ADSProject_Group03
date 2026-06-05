#include "verilated.h"
#include "VALU.h"
#include <iostream>

void run_test(VALU* dut, const char* op_name, int a, int b, int op) {
    dut->io_operandA = a;
    dut->io_operandB = b;
    dut->io_operation = op;

    dut->eval();

    std::cout << op_name << " | "
              << "A=" << a << " B=" << b
              << " => Result=" << dut->io_aluResult
              << std::endl;
}

int main(int argc, char **argv) {
    Verilated::commandArgs(argc, argv);

    VALU* dut = new VALU;

    int a = 16;
    int b = 5;

    run_test(dut, "ADD", a, b, 0);
    run_test(dut, "SUB", a, b, 1);
    run_test(dut, "AND", a, b, 2);
    run_test(dut, "OR",  a, b, 3);
    run_test(dut, "XOR", a, b, 4);
    run_test(dut, "SLL", a, b, 5);
    run_test(dut, "SRL", a, b, 6);
    run_test(dut, "SRA", a, b, 7);
    run_test(dut, "SLT", a, b, 8);
    run_test(dut, "SLTU",a, b, 9);
    run_test(dut, "PASS B", a, b, 10);

    delete dut;
    return 0;
}