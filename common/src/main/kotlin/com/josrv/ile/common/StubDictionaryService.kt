package com.josrv.ile.common


class StubDictionaryService : DictionaryService {
    private val definitions = mapOf(
        "have" to listOf(
            Definition("have", POS.VERB, "To possess, own, hold."),
            Definition(
                "have",
                POS.VERB,
                "To be related in some way to (with the object identifying the relationship)."
            ),
            Definition(
                "have",
                POS.VERB,
                "To partake of a particular substance (especially a food or drink) or action."
            )
        ),
        "human" to listOf(
            Definition(
                "human",
                POS.NOUN,
                "A human being, whether man, woman or child."
            ),
            Definition(
                "human",
                POS.ADJECTIVE,
                "Of or belonging to the species Homo sapiens or its closest relatives. "
            )
        )
    )

    override fun lookup(word: String): List<Definition> {
        return definitions.getOrElse(word) { emptyList() }
    }
}