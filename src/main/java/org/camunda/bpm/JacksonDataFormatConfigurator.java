package org.camunda.bpm;/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *camunda-spin-dataformat-json-jackson
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat;
import org.camunda.spin.spi.DataFormatConfigurator;

public class JacksonDataFormatConfigurator implements DataFormatConfigurator<JacksonJsonDataFormat> {

    /**
     * Overrides the default configuration of the jackson object mapper, to ignore the unknown filter ids
     *
     * @param dataFormat data format
     */
    public void configure(JacksonJsonDataFormat dataFormat) {
        final ObjectMapper objectMapper = dataFormat.getObjectMapper();
        FilterProvider filterProvider = objectMapper.getSerializationConfig().getFilterProvider();
        if (filterProvider == null) {
            filterProvider = new SimpleFilterProvider().setFailOnUnknownId(false);
        } else if (filterProvider instanceof SimpleFilterProvider) {
            ((SimpleFilterProvider) filterProvider).setFailOnUnknownId(false);
        }
        System.out.println("I was called---------------xxxxxxxxxxxxxxxxxxxxxx-----------------");
        final SerializationConfig newConfig = objectMapper.getSerializationConfig().withFilters(filterProvider)
                .withSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setConfig(newConfig);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Class<JacksonJsonDataFormat> getDataFormatClass() {
        return JacksonJsonDataFormat.class;
    }

}
