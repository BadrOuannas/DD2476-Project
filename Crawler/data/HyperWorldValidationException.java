25
https://raw.githubusercontent.com/Sauilitired/Hyperverse/master/Core/src/main/java/se/hyperver/hyperverse/exception/HyperWorldValidationException.java
//
// Hyperverse - A Minecraft world management plugin
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
//

package se.hyperver.hyperverse.exception;

import se.hyperver.hyperverse.world.HyperWorld;
import se.hyperver.hyperverse.world.HyperWorldCreator;
import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when the validation of a {@link HyperWorld} fails
 */
public class HyperWorldValidationException extends HyperException {

    private final HyperWorldCreator.ValidationResult validationResult;

    public HyperWorldValidationException(
        @NotNull final HyperWorldCreator.ValidationResult validationResult,
        @NotNull final HyperWorld world) {
        super(world, String
            .format("Failed to validate world configuration for world %s. Result: %s",
                world.getConfiguration().getName(), validationResult.name()));
        this.validationResult = validationResult;
    }

    @NotNull public HyperWorldCreator.ValidationResult getValidationResult() {
        return this.validationResult;
    }

}
