/**
 * Copyright (c) 2012 to Continuuity Inc. All rights reserved.
 * Licensed to Odiago, Inc.
 */
package com.continuuity.common.options;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Option {
  /**
   * Specifies the name of the command line option to be associated with the variable of a class.
   * @return name associated with class variable.
   */
  String name() default "";

  /**
   * Specifies the usage.
   * @return usage info.
   */
  String usage() default "";

  /**
   * Specifies the type of parameter.
   * @return type parameter.
   */
  String type() default "";

  /**
   * Specifies whether a variable annotated with @Option need to be displayed as part of usage.
   * @return true to hide; false otherwise.
   */
  boolean hidden() default false;

  /**
   * Specifies the environment variable to be associated a field.
   * @return environment variable to be read for a field.
   */
  String envVar() default "";
}
