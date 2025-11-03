/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.javaagent;

import com.google.auto.service.AutoService;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigurablePropagatorProvider;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Registers the custom propagator used by this example.
 *
 * @author LASER
 * @see ConfigurablePropagatorProvider
 * @see DemoPropagator
 */
@AutoService(ConfigurablePropagatorProvider.class)
public class DemoPropagatorProvider implements ConfigurablePropagatorProvider {
  @Override
  public TextMapPropagator getPropagator(@NonNull ConfigProperties config) {
    return new DemoPropagator();
  }

  @Override
  public String getName() {
    return "ssf-customized-propagator";
  }
}
