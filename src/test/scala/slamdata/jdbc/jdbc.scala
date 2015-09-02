/*
 * Copyright 2014 - 2015 SlamData Inc.
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

package slamdata.jdbc

import org.specs2.mutable._

class DriverSpecs extends Specification {
  "SlamDataDriver" should {

    "connect to local server" in {
      val driver = new SlamDataDriver()
      
      val cxn = driver.connect("slamengine://104.236.166.167:8080/demo/", null)
            
      cxn.close()
      
      success
    }

    "run simple query" in {
      val driver = new SlamDataDriver()
      
      val cxn = driver.connect("slamengine://104.236.166.167:8080/demo/", null)
      try {

        val stmt = cxn.createStatement
      
        val rs = stmt.executeQuery("select count(*) from zips")
      
        val hasNext = rs.next
      
        val rez = rs.getString("0")
        rez must_== "29353"
      }
      finally {
        cxn.close()
      }
    }
  }
  
  step {
    // cleanup threads for SBT purposes:
    dispatch.Http.shutdown  
  }
}
