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

import org.jreversepro.ast.expression.ArrayMemberReferenceExpression;
import org.jreversepro.ast.expression.Assignment;
import org.jreversepro.ast.expression.Expression;
import org.jreversepro.ast.intermediate.CompleteLine;
import org.jreversepro.jvm.TypeInferrer;
import org.jreversepro.reflect.instruction.Instruction;


public class ArrayIndexStoreEvaluator extends AbstractInstructionEvaluator {

  public ArrayIndexStoreEvaluator(EvaluatorContext context) {
    super(context);
  }

  @Override
  void evaluate(Instruction ins) {
    Expression value = evalMachine.pop();
    Expression subscript = evalMachine.pop();
    Expression arrayObject = evalMachine.pop();

    ArrayMemberReferenceExpression arr = new ArrayMemberReferenceExpression(
        arrayObject, subscript, TypeInferrer.getArrayMemberType(arrayObject
            .getType()));

    statements.append(new CompleteLine(ins, new Assignment(arr, value)));
  }

  @Override
  Iterable<Integer> getProcessingOpcodes() {
    return Arrays.asList(OPCODE_IASTORE, OPCODE_LASTORE, OPCODE_FASTORE,
        OPCODE_DASTORE, OPCODE_AASTORE, OPCODE_BASTORE, OPCODE_CASTORE,
        OPCODE_SASTORE);
  }

}
