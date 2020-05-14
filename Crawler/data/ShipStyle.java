74
https://raw.githubusercontent.com/rayfowler/rotp-public/master/src/rotp/model/ships/ShipStyle.java
/*
 * Copyright 2015-2020 Ray Fowler
 * 
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://www.gnu.org/licenses/gpl-3.0.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rotp.model.ships;

import java.util.ArrayList;
import java.util.List;

public class ShipStyle {
    String name;
    List<ShipImage> images = new ArrayList<>();
    public ShipStyle(String s) {
        name = s;
    }
}