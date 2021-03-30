package instruction_interpreters

interface IInstructionInterpreter<I> {
    fun getEntitiesList(): List<I>
}