package com.javacase.accountingapi.common.usecase;

public interface VoidUseCaseHandler<T extends UseCase> {

  void handle(T useCase);
}
