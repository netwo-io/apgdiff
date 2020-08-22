/**
 * Copyright 2006 StartNet s.r.o.
 *
 * Distributed under MIT license
 */
package cz.startnet.utils.pgdiff.parsers

import cz.startnet.utils.pgdiff.Resources
import cz.startnet.utils.pgdiff.schema.PgColumn
import cz.startnet.utils.pgdiff.schema.PgDatabase
import cz.startnet.utils.pgdiff.schema.PgType
import java.text.MessageFormat

/**
 * Parses CREATE TABLE statements.
 *
 * @author fordfrog
 */
object CreateTypeParser {
    /**
     * Parses CREATE TYPE statement.
     *
     * @param database database
     * @param statement CREATE TYPE statement
     */
    fun parse(
        database: PgDatabase,
        statement: String
    ) {
        val parser = Parser(statement)
        parser.expect("CREATE", "TYPE")
        val typeName = parser.parseIdentifier()
        val type = PgType(ParserUtils.getObjectName(typeName))
        val schemaName = ParserUtils.getSchemaName(typeName, database)
        val schema = database.getSchema(schemaName)
                ?: throw RuntimeException(
                    MessageFormat.format(
                        Resources.getString("CannotFindSchema"), schemaName,
                        statement
                    )
                )
        schema.addType(type)
        parser.expect("AS")
        if (parser.expectOptional("ENUM")) {
            type.isEnum = true
        }
        parser.expect("(")
        while (!parser.expectOptional(")")) {
            if (type.isEnum) {
                val name = parser.expression
                type.addEnumValue(name)
                if (parser.expectOptional(")")) {
                    break
                } else {
                    parser.expect(",")
                }
            } else {
                parseColumn(parser, type)
                if (parser.expectOptional(")")) {
                    break
                } else {
                    parser.expect(",")
                }
            }
        }
        while (!parser.expectOptional(";")) {
        }
    }

    /**
     * Parses column definition.
     *
     * @param parser parser
     * @param type type
     */
    private fun parseColumn(parser: Parser, type: PgType) {
        val column = PgColumn(
            ParserUtils.getObjectName(parser.parseIdentifier())
        )
        type.addColumn(column)
        column.parseDefinition(parser.expression)
    }
}