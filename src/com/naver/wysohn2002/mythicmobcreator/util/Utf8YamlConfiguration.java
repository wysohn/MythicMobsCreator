/*******************************************************************************
 *     Copyright (C) 2017 wysohn
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.naver.wysohn2002.mythicmobcreator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

public class Utf8YamlConfiguration extends YamlConfiguration {

    public static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    @Override
    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
            Validate.notNull(stream, "Stream cannot be null");

            InputStreamReader reader = new InputStreamReader(stream, UTF8_CHARSET);
            StringBuilder builder = new StringBuilder();
            BufferedReader input = new BufferedReader(reader);

            try {
                    String line;

                    while ((line = input.readLine()) != null) {
                            builder.append(line);
                            builder.append('\n');
                    }
            } finally {
                    input.close();
            }

            loadFromString(builder.toString());
    }

    @Override
    public void save(File file) throws IOException {
            Validate.notNull(file, "File cannot be null");

            Files.createParentDirs(file);

            String data = saveToString();

            FileOutputStream stream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(stream, UTF8_CHARSET);

            try {
                    writer.write(data);
            } finally {
                    writer.close();
            }
    }
}
