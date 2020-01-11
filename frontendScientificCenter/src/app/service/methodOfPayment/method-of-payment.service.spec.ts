import { TestBed } from '@angular/core/testing';

import { MethodOfPaymentService } from './method-of-payment.service';

describe('MethodOfPaymentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MethodOfPaymentService = TestBed.get(MethodOfPaymentService);
    expect(service).toBeTruthy();
  });
});
