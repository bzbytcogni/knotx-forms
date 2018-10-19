/*
 * Copyright (C) 2018 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.forms.adapter.mock;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Options describing how an ServiceAdapter will make connections with external HTTP services.
 */

@DataObject(generateConverter = true, publicConverter = false)
public class FormsMockAdapterOptions {

  /**
   * Default EB address of the adapter = knotx.adapter.service.http
   */
  public final static String DEFAULT_ADDRESS = "knotx.forms.http";

  private String address;

  /**
   * Default constructor
   */
  public FormsMockAdapterOptions() {
    init();
  }


  /**
   * Copy constructor
   *
   * @param other the instance to copy
   */
  public FormsMockAdapterOptions(
      FormsMockAdapterOptions other) {
    this.address = other.address;
  }

  /**
   * Create an settings from JSON
   *
   * @param json the JSON
   */
  public FormsMockAdapterOptions(JsonObject json) {
    init();
    FormsMockAdapterOptionsConverter.fromJson(json, this);
  }

  /**
   * Convert to JSON
   *
   * @return the JSON
   */
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    FormsMockAdapterOptionsConverter.toJson(this, json);
    return json;
  }

  private void init() {
    address = DEFAULT_ADDRESS;
  }

  /**
   * @return event bus address the service adapter is registered on
   */
  public String getAddress() {
    return address;
  }

  /**
   * Event Bus {@code address} the service adapter listening on. Default value is
   * 'knotx.adapter.service.http'
   *
   * @param address an event bus address
   * @return a reference to this, so the API can be used fluently
   */
  public FormsMockAdapterOptions setAddress(
      String address) {
    this.address = address;
    return this;
  }

}
