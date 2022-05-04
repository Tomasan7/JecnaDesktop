package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

public record MenuItem(@NotNull String description,
					   @NotNull String allergens)
{
}
