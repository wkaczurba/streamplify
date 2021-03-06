/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beryx.streamplify.derangement;

import org.beryx.streamplify.BigIntegerIndexedSpliterator;

import java.math.BigInteger;

/**
 * Provides streams of derangements.
 * <br>For derangements with a length <= 21, you may consider using the more efficient {@link LongDerangements}.
 */
@SuppressWarnings("unchecked")
public class BigIntegerDerangements extends BigIntegerIndexedSpliterator<int[], BigIntegerDerangements> {
    public static final int MAX_LENGTH = 20_000;

    /**
     * Constructs derangements of {@code length} elements
     */
    public BigIntegerDerangements(int length) {
        super(BigInteger.ZERO, subfactorial(length));
        this.withValueSupplier(new DerangementSupplier.BigInt(length));
        this.withAdditionalCharacteristics(DISTINCT);
    }

    /**
     * @throws IllegalArgumentException if {@code n} is negative or too big (> {@value #MAX_LENGTH})
     */
    public static BigInteger subfactorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Invalid derangement length: " + n);
        if (n > MAX_LENGTH) throw new IllegalArgumentException("Value too big: " + n);
        if (n == 0) return BigInteger.ONE;
        BigInteger prev = BigInteger.ONE;
        BigInteger curr = BigInteger.ZERO;
        for (int i = 2; i <= n; ++i) {
            BigInteger next = BigInteger.valueOf(i - 1).multiply(curr.add(prev));
            prev = curr;
            curr = next;
        }
        return curr;
    }
}
