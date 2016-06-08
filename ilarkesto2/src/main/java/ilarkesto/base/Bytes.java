/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.base;

import static java.lang.Math.round;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;

/**
 * Data type for storing amount of bytes.
 */
public class Bytes implements Comparable<Bytes> {

        private long bytes;

        public Bytes(long bytes) {
                this.bytes = bytes;
        }

        public long toLong() {
                return bytes;
        }

        @Override
        public String toString() {
                return valueOf(bytes) + " Bytes";
        }

        public String toRoundedString() {
		if (bytes > 10000000000l) {
                        return valueOf(round(bytes / 1000000000f)) + " GB";
                }
		if (bytes > 10000000) {
                        return valueOf(round(bytes / 1000000f)) + " MB";
                }
		if (bytes > 10000) {
                        return valueOf(round(bytes / 1000f)) + " KB";
                }
                return toString();
        }

        public static Bytes kilo(long kilobytes) {
                return new Bytes(kilobytes * 1000);
        }

        public static Bytes mega(long megabytes) {
                return new Bytes(megabytes * 1000000);
        }

        public static Bytes giga(long gigabytes) {
                return new Bytes(gigabytes * 1000000000);
        }

	@Override
        public int compareTo(Bytes o) {
                return bytes > o.bytes ? 1 : -1;
        }

        @Override
        public int hashCode() {
                int hash = 3;
                hash = 31 * hash + (int) (this.bytes ^ (this.bytes >>> 32));
                return hash;
        }

        @Override
        public boolean equals(Object obj) {
                if (obj == null) {
                        return false;
                }
                if (getClass() != obj.getClass()) {
                        return false;
                }
                final Bytes other = (Bytes) obj;
                return this.bytes == other.bytes;
        }
}
