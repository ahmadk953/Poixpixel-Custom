package com.poixpixelcustom.Object

import com.google.common.base.Preconditions
import com.poixpixelcustom.Object.Metadata.CustomDataField
import org.jetbrains.annotations.Unmodifiable
import java.util.*

abstract class PoixpixelCustomObject protected constructor(override var name: String) : Nameable, Savable {
    private var metadata: MutableMap<String, CustomDataField<*>?>? = null
    fun getTreeString(depth: Int): List<String> {
        return ArrayList()
    }

    fun getTreeDepth(depth: Int): String {
        val fill = CharArray(depth * 4)
        Arrays.fill(fill, ' ')
        if (depth > 0) {
            fill[0] = '|'
            val offset = (depth - 1) * 4
            fill[offset] = '+'
            fill[offset + 1] = '-'
            fill[offset + 2] = '-'
        }
        return kotlin.String()
    }

    override fun toString(): String {
        return name
    }
    /**
     * Add a specific metadata to this PoixpixelCustomObject.
     * Overrides existing metadata of the same key.
     *
     * @param md CustomDataField to add.
     * @param save whether to save this object after the metadata is added.
     */
    // Exists to maintain backwards compatibility
    // DO NOT OVERRIDE THIS METHOD ANYWHERE
    /**
     * Add a specific metadata to this PoixpixelCustomObject.
     * Overrides existing metadata of the same key.
     * Most implementations will save the object after this method is called.
     *
     * @param md CustomDataField to add.
     */
    @JvmOverloads
    fun addMetaData(md: CustomDataField<*>, save: Boolean = false) {
        Preconditions.checkNotNull(md)
        if (metadata == null) metadata = HashMap()
        metadata!![md.key] = md
        if (save) save()
    }

    /**
     * Remove a specific metadata from the PoixpixelCustomObject.
     * The metadata does not need to be the same instance of the one added,
     * but must have the same key.
     * Most implementations will save the PoixpixelCustomObject after removing the metadata.
     *
     * @param md CustomDataField to remove.
     */
    fun removeMetaData(md: CustomDataField<*>) {
        this.removeMetaData(md, false)
    }

    /**
     * Remove a specific metadata from the PoixpixelCustomObject.
     * The metadata does not need to be the same instance of the one added,
     * but must have the same key.
     *
     * @param md CustomDataField to remove.
     * @param save whether to save the object or not after the metadata is removed.
     *
     * @return whether the metadata was successfully removed.
     */
    // Exists to maintain backwards compatibility
    // DO NOT OVERRIDE THIS METHOD ANYWHERE
    fun removeMetaData(md: CustomDataField<*>, save: Boolean): Boolean {
        Preconditions.checkNotNull(md)
        return removeMetaData(md.key, save)
    }
    /**
     * Remove a specific metadata from the PoixpixelCustomObject.
     *
     * @param key Key of the data field to remove.
     * @param save whether to save the object or not after the metadata is removed.
     *
     * @return whether the metadata was successfully removed.
     */
    /**
     * Remove a specific metadata from the PoixpixelCustomObject.
     * Most implementations will save the PoixpixelCustomObject after removing the metadata.
     *
     * @param key Key of the data field to remove.
     * @return whether the metadata was successfully removed.
     */
    @JvmOverloads
    fun removeMetaData(key: String, save: Boolean = false): Boolean {
        Preconditions.checkNotNull(key)
        if (!hasMeta()) return false
        val removed = metadata!!.remove(key) != null
        if (metadata!!.isEmpty()) metadata = null

        // Only save if the element was actually removed
        if (save && removed) save()
        return removed
    }

    /**
     * A collection of all metadata on the PoixpixelCustomObject.
     * This collection cannot be modified.
     *
     * Collection reflects current metadata, and is not thread safe.
     *
     * @return an unmodifiable collection of all metadata on the object.
     */
    fun getMetadata(): Collection<CustomDataField<*>?>? {
        return if (metadata == null || metadata!!.isEmpty()) emptyList<CustomDataField<*>>() else Collections.unmodifiableCollection(metadata!!.values)
    }

    /**
     * Fetch the metadata associated with the specific key.
     *
     * @param key Key of the metadata to fetch.
     *
     * @return the metadata associated with the key or `null` if none associated.
     */
    fun getMetadata(key: String): CustomDataField<*>? {
        Preconditions.checkNotNull(key)
        return if (metadata != null) metadata!![key] else null
    }

    /**
     * Fetch the metadata associated with the specific key and class.
     *
     * @param <T> The Class.
     * @param key Key of the metadata to fetch.
     * @param cdfClass Class of the CustomDataField to fetch.
     *
     * @return the specific metadata associated with the key and class or `null` if none exist.
    </T> */
    fun <T : CustomDataField<*>?> getMetadata(key: String, cdfClass: Class<T>): T? {
        Preconditions.checkNotNull(cdfClass)
        Preconditions.checkNotNull(key)
        if (metadata != null) {
            val cdf = metadata!![key]
            if (cdfClass.isInstance(cdf)) {
                return cdf as T?
            }
        }
        return null
    }

    /**
     *
     * @return whether this object has metadata or not.
     */
    fun hasMeta(): Boolean {
        return metadata != null
    }

    /**
     * Check whether metadata associated with the key exists.
     *
     * @param key Key of the metadata to check.
     * @return whether metadata associated with the key exists.
     */
    fun hasMeta(key: String): Boolean {
        Preconditions.checkNotNull(key)
        return if (metadata != null) metadata!!.containsKey(key) else false
    }

    /**
     * Check whether metadata associated with the given key and class exists.
     *
     * @param <T> The Class.
     * @param key Key of the metadata to check
     * @param cdfClass Class extending CustomDataField to check.
     *
     * @return whether metadata associated with the key and class exists.
    </T> */
    fun <T : CustomDataField<*>?> hasMeta(key: String, cdfClass: Class<T>): Boolean {
        Preconditions.checkNotNull(cdfClass)
        Preconditions.checkNotNull(key)
        if (metadata != null) {
            val cdf = metadata!![key]
            return cdfClass.isInstance(cdf)
        }
        return false
    }
}