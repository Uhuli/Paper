package com.destroystokyo.paper;

import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import java.util.Objects;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;

public enum VersionHistoryManager {
    INSTANCE;

    private final Gson gson = new Gson();

    private final Logger logger = Bukkit.getLogger();

    private VersionData currentData = null;

    @Nullable
    public VersionData getVersionData() {
        return currentData;
    }

    public static class VersionData {
        private String oldVersion;

        private String currentVersion;

        @Nullable
        public String getOldVersion() {
            return oldVersion;
        }

        public void setOldVersion(@Nullable String oldVersion) {
            this.oldVersion = oldVersion;
        }

        @Nullable
        public String getCurrentVersion() {
            return currentVersion;
        }

        public void setCurrentVersion(@Nullable String currentVersion) {
            this.currentVersion = currentVersion;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                .add("oldVersion", oldVersion)
                .add("currentVersion", currentVersion)
                .toString();
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final VersionData versionData = (VersionData) o;
            return Objects.equals(oldVersion, versionData.oldVersion) &&
                Objects.equals(currentVersion, versionData.currentVersion);
        }

        @Override
        public int hashCode() {
            return Objects.hash(oldVersion, currentVersion);
        }
    }
}
