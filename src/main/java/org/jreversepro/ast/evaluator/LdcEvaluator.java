/**
 * JReversePro - Java Decompiler / Disassembler.
 * Copyright (C) 2008 Karthik Kumar.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0 
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package org.jreversepro.ast.evaluator;


import java.util.Arrays;

import org.jreversepro.ast.expression.Constant;
import org.jreversepro.reflect.instruction.Instruction;


public class LdcEvaluator extends AbstractInstructionEvaluator {

  public LdcEvaluator(EvaluatorContext context) {
    super(context);
  }

  @Override
  void evaluate(Instruction ins) {
    switch (ins.opcode) {
    case OPCODE_LDC: {
      // Utf8 Value is referred to here.
      int ldcIndex = ins.getArgUnsignedByte();
      String ldcString = pool.getLdcString(ldcIndex);
      evalMachine.push(new Constant(ldcString, pool.getDataType(ldcIndex)));
      break;
    }
    case OPCODE_LDC_W: { // ldc_w
      int ldcIndex = ins.getArgUnsignedShort();
      evalMachine.push(new Constant(pool.getLdcString(ldcIndex),
          CLASS_LANG_STRING));
      break;
    }
    case OPCODE_LDC2_W: { // ldc2_w
      int ldcIndex = ins.getArgUnsignedShort();
      evalMachine.push(new Constant(pool.getEntryValue(ldcIndex), pool
          .getDataType(ldcIndex)));
      break;
    }
    }
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_LDC, OPCODE_LDC_W, OPCODE_LDC2_W);
  }
}
