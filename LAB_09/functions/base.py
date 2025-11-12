
from abc import ABC, abstractmethod
from typing import Any

class MathFunction(ABC):
    """Абстрактний базовий клас для математичних функцій."""

    @abstractmethod
    def calculate(self, x: float) -> float:
        """Обчислює значення функції."""
        pass

    @abstractmethod
    def __str__(self) -> str:
        """Повертає рядкове представлення функції."""
        pass

    def validate_input(self, x: float) -> None:
        """Перевірка коректності вхідного значення."""
        if not isinstance(x, (int, float)):
            raise TypeError("x має бути числом")