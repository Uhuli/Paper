package org.bukkit;

import java.util.UUID;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents a reference to a player identity and the data belonging to a
 * player that is stored on the disk and can, thus, be retrieved without the
 * player needing to be online.
 */
@NullMarked
public interface OfflinePlayer extends AnimalTamer, ConfigurationSerializable, io.papermc.paper.persistence.PersistentDataViewHolder { // Paper - Add Offline PDC API

    /**
     * Checks if this player is currently online
     * It should be noted that this will return true if any instance of this player is
     * online! This instance may have disconnected. If you wish to check if this specific
     * instance of the player is still online, see {@link OfflinePlayer#isConnected()}.
     *
     * @return true if they are online
     */
    public boolean isOnline();

    // Paper start
    /**
     * Checks whether the connection to this player is still valid. This will return
     * true as long as this specific instance of the player is still connected. This
     * will return false after this instance has disconnected, even if the same player
     * has reconnected since.
     *
     * @return true if this player instance is connected
     */
    public boolean isConnected();
    // Paper end

    /**
     * Returns the name of this player
     * <p>
     * Names are no longer unique past a single game session. For persistent storage
     * it is recommended that you use {@link #getUniqueId()} instead.
     *
     * @return Player name or null if we have not seen a name for this player yet
     */
    @Override
    @Nullable
    public String getName();

    /**
     * Returns the UUID of this player
     *
     * @return Player UUID
     */
    @Override
    public UUID getUniqueId();

    /**
     * Gets a copy of the player's profile.
     * <p>
     * If the player is online, the returned profile will be complete.
     * Otherwise, only the unique id is guaranteed to be present. You can use
     * {@link PlayerProfile#update()} to complete the returned profile.
     *
     * @return the player's profile
     */
    com.destroystokyo.paper.profile.PlayerProfile getPlayerProfile(); // Paper

    /**
     * Gets a {@link Player} object that this represents, if there is one
     * <p>
     * If the player is online, this will return that player. Otherwise,
     * it will return null.
     *
     * @return Online player
     */
    @Nullable
    public Player getPlayer();

    /**
     * Gets the first date and time that this player was witnessed on this
     * server.
     * <p>
     * If the player has never played before, this will return 0. Otherwise,
     * it will be the amount of milliseconds since midnight, January 1, 1970
     * UTC.
     *
     * @return Date of first log-in for this player, or 0
     */
    public long getFirstPlayed();

    /**
     * Gets the last date and time that this player was witnessed on this
     * server.
     * <p>
     * If the player has never played before, this will return 0. Otherwise,
     * it will be the amount of milliseconds since midnight, January 1, 1970
     * UTC.
     *
     * @return Date of last log-in for this player, or 0
     * @deprecated The API contract is ambiguous and the implementation may or may not return the correct value given this API ambiguity. It is instead recommended use {@link #getLastLogin()} or {@link #getLastSeen()} depending on your needs.
     */
    @Deprecated
    public long getLastPlayed();

    /**
     * Checks if this player has played on this server before.
     *
     * @return True if the player has played before, otherwise false
     */
    public boolean hasPlayedBefore();

    /**
     * Gets the Location where the player will spawn at their bed, null if
     * they have not slept in one or their current bed spawn is invalid.
     *
     * @return Bed Spawn Location if bed exists, otherwise null.
     *
     * @see #getRespawnLocation()
     * @deprecated Misleading name. This method also returns the location of
     * respawn anchors.
     */
    @Nullable
    @Deprecated(since = "1.20.4")
    public Location getBedSpawnLocation();
    // Paper start
    /**
     * Gets the last date and time that this player logged into the server.
     * <p>
     * If the player has never played before, this will return 0. Otherwise,
     * it will be the amount of milliseconds since midnight, January 1, 1970
     * UTC.
     *
     * @return last login time
     */
    public long getLastLogin();

    /**
     * Gets the last date and time that this player was seen on the server.
     * <p>
     * If the player has never played before, this will return 0. If the
     * player is currently online, this will return the current time.
     * Otherwise it will be the amount of milliseconds since midnight,
     * January 1, 1970 UTC.
     *
     * @return last seen time
     */
    public long getLastSeen();
    // Paper end

    /**
     * Gets the Location where the player will spawn at, null if they
     * don't have a valid respawn point.
     *
     * @return respawn location if exists, otherwise null.
     */
    @Nullable
    public Location getRespawnLocation();

    /**
     * Increments the given statistic for this player.
     * <p>
     * This is equivalent to the following code:
     * <code>incrementStatistic(Statistic, 1)</code>
     *
     * @param statistic Statistic to increment
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if the statistic requires an
     *     additional parameter
     */
    public void incrementStatistic(Statistic statistic) throws IllegalArgumentException;

    /**
     * Decrements the given statistic for this player.
     * <p>
     * This is equivalent to the following code:
     * <code>decrementStatistic(Statistic, 1)</code>
     *
     * @param statistic Statistic to decrement
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if the statistic requires an
     *     additional parameter
     */
    public void decrementStatistic(Statistic statistic) throws IllegalArgumentException;

    /**
     * Increments the given statistic for this player.
     *
     * @param statistic Statistic to increment
     * @param amount Amount to increment this statistic by
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if amount is negative
     * @throws IllegalArgumentException if the statistic requires an
     *     additional parameter
     */
    public void incrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException;

    /**
     * Decrements the given statistic for this player.
     *
     * @param statistic Statistic to decrement
     * @param amount Amount to decrement this statistic by
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if amount is negative
     * @throws IllegalArgumentException if the statistic requires an
     *     additional parameter
     */
    public void decrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException;

    /**
     * Sets the given statistic for this player.
     *
     * @param statistic Statistic to set
     * @param newValue The value to set this statistic to
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if newValue is negative
     * @throws IllegalArgumentException if the statistic requires an
     *     additional parameter
     */
    public void setStatistic(Statistic statistic, int newValue) throws IllegalArgumentException;

    /**
     * Gets the value of the given statistic for this player.
     *
     * @param statistic Statistic to check
     * @return the value of the given statistic
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if the statistic requires an
     *     additional parameter
     */
    public int getStatistic(Statistic statistic) throws IllegalArgumentException;

    /**
     * Increments the given statistic for this player for the given material.
     * <p>
     * This is equivalent to the following code:
     * <code>incrementStatistic(Statistic, Material, 1)</code>
     *
     * @param statistic Statistic to increment
     * @param material Material to offset the statistic with
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if material is null
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void incrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException;

    /**
     * Decrements the given statistic for this player for the given material.
     * <p>
     * This is equivalent to the following code:
     * <code>decrementStatistic(Statistic, Material, 1)</code>
     *
     * @param statistic Statistic to decrement
     * @param material Material to offset the statistic with
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if material is null
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void decrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException;

    /**
     * Gets the value of the given statistic for this player.
     *
     * @param statistic Statistic to check
     * @param material Material offset of the statistic
     * @return the value of the given statistic
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if material is null
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public int getStatistic(Statistic statistic, Material material) throws IllegalArgumentException;

    /**
     * Increments the given statistic for this player for the given material.
     *
     * @param statistic Statistic to increment
     * @param material Material to offset the statistic with
     * @param amount Amount to increment this statistic by
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if material is null
     * @throws IllegalArgumentException if amount is negative
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void incrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException;

    /**
     * Decrements the given statistic for this player for the given material.
     *
     * @param statistic Statistic to decrement
     * @param material Material to offset the statistic with
     * @param amount Amount to decrement this statistic by
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if material is null
     * @throws IllegalArgumentException if amount is negative
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void decrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException;

    /**
     * Sets the given statistic for this player for the given material.
     *
     * @param statistic Statistic to set
     * @param material Material to offset the statistic with
     * @param newValue The value to set this statistic to
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if material is null
     * @throws IllegalArgumentException if newValue is negative
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void setStatistic(Statistic statistic, Material material, int newValue) throws IllegalArgumentException;

    /**
     * Increments the given statistic for this player for the given entity.
     * <p>
     * This is equivalent to the following code:
     * <code>incrementStatistic(Statistic, EntityType, 1)</code>
     *
     * @param statistic Statistic to increment
     * @param entityType EntityType to offset the statistic with
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if entityType is null
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void incrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException;

    /**
     * Decrements the given statistic for this player for the given entity.
     * <p>
     * This is equivalent to the following code:
     * <code>decrementStatistic(Statistic, EntityType, 1)</code>
     *
     * @param statistic Statistic to decrement
     * @param entityType EntityType to offset the statistic with
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if entityType is null
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void decrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException;

    /**
     * Gets the value of the given statistic for this player.
     *
     * @param statistic Statistic to check
     * @param entityType EntityType offset of the statistic
     * @return the value of the given statistic
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if entityType is null
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public int getStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException;

    /**
     * Increments the given statistic for this player for the given entity.
     *
     * @param statistic Statistic to increment
     * @param entityType EntityType to offset the statistic with
     * @param amount Amount to increment this statistic by
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if entityType is null
     * @throws IllegalArgumentException if amount is negative
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void incrementStatistic(Statistic statistic, EntityType entityType, int amount) throws IllegalArgumentException;

    /**
     * Decrements the given statistic for this player for the given entity.
     *
     * @param statistic Statistic to decrement
     * @param entityType EntityType to offset the statistic with
     * @param amount Amount to decrement this statistic by
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if entityType is null
     * @throws IllegalArgumentException if amount is negative
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void decrementStatistic(Statistic statistic, EntityType entityType, int amount);

    /**
     * Sets the given statistic for this player for the given entity.
     *
     * @param statistic Statistic to set
     * @param entityType EntityType to offset the statistic with
     * @param newValue The value to set this statistic to
     * @throws IllegalArgumentException if statistic is null
     * @throws IllegalArgumentException if entityType is null
     * @throws IllegalArgumentException if newValue is negative
     * @throws IllegalArgumentException if the given parameter is not valid
     *     for the statistic
     */
    public void setStatistic(Statistic statistic, EntityType entityType, int newValue);

    /**
     * Gets the player's last death location.
     *
     * @return the last death location if it exists, otherwise null.
     */
    @Nullable
    public Location getLastDeathLocation();

    /**
     * Gets the player's current location.
     *
     * @return the player's location, {@code null} if player hasn't ever played
     * before.
     */
    @Nullable
    public Location getLocation();
    // Paper start - add pdc to offline player
    /**
     * Yields a view of the persistent data container for this offline player.
     * In case this {@link OfflinePlayer} instance was created for an offline player, the returned view will wrap the persistent
     * data on disk.
     * <p>
     * As such, this method as well as queries to the {@link io.papermc.paper.persistence.PersistentDataContainerView}
     * may produce blocking IO requests to read the requested data from disk.
     * Caution in its usage is hence advised.
     *
     * @return the persistent data container view
     * @see io.papermc.paper.persistence.PersistentDataViewHolder#getPersistentDataContainer()
     */
    @Override
    io.papermc.paper.persistence.PersistentDataContainerView getPersistentDataContainer();
    // Paper end - add pdc to offline player
}
