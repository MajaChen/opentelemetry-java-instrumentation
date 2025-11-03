/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.javaagent;

import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.context.propagation.TextMapSetter;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.Arrays;
import java.util.List;

/**
 * See <a
 * href="https://github.com/open-telemetry/opentelemetry-specification/blob/master/specification/context/api-propagators.md">
 * OpenTelemetry Specification</a> for more information about Propagators.
 *
 * @author LASER
 * @see DemoPropagatorProvider
 */
public class DemoPropagator implements TextMapPropagator {
  private static final String X_REQUEST_ID_FIELD = "x-request-id";
  private static final String X_OT_SPAN_CONTEXT_FIELD = "x-ot-span-context";
  private static final ContextKey<String> REQUEST_ID_CONTEXT_KEY =
      ContextKey.named("x.request.id");
  private static final ContextKey<String> OT_SPAN_CONTEXT_KEY =
      ContextKey.named("x.ot.span.context");

  @Override
  public List<String> fields() {
    return Arrays.asList(X_REQUEST_ID_FIELD, X_OT_SPAN_CONTEXT_FIELD);
  }

  @Override
  public <C> void inject(Context context, C carrier, @NonNull TextMapSetter<C> setter) {
    String requestId = context.get(REQUEST_ID_CONTEXT_KEY);
    String otSpanContext = context.get(OT_SPAN_CONTEXT_KEY);
    if (requestId != null) {
      setter.set(carrier, X_REQUEST_ID_FIELD, requestId);
    }
    if (otSpanContext != null) {
      setter.set(carrier, X_OT_SPAN_CONTEXT_FIELD, otSpanContext);
    }
  }

  @Override
  public <C> Context extract(@NonNull Context context, C carrier, TextMapGetter<C> getter) {
    String requestId = getter.get(carrier, X_REQUEST_ID_FIELD);
    String otSpanContext = getter.get(carrier, X_OT_SPAN_CONTEXT_FIELD);
    if (requestId != null) {
      context = context.with(REQUEST_ID_CONTEXT_KEY, requestId);
    }
    if (otSpanContext != null) {
      context = context.with(OT_SPAN_CONTEXT_KEY, otSpanContext);
    }
    return context;
  }
}
