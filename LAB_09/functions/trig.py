
import math
from .base import MathFunction

class SinOverTan4x(MathFunction):
    """Обчислює y = sin(x) / tg(4x)"""

    def calculate(self, x: float) -> float:
        self.validate_input(x)
        if abs(x) < 1e-12:
            raise ValueError("x ≈ 0 → tg(4x) невизначена")
        tan_4x = math.tan(4 * x)
        if abs(tan_4x) < 1e-10:
            raise ZeroDivisionError("Ділення на нуль: tg(4x) ≈ 0")
        return math.sin(x) / tan_4x

    def __str__(self) -> str:
        return "y = sin(x) / tg(4x)"