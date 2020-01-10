import { TestBed } from '@angular/core/testing';

import { ScientificAreaService } from './scientific-area.service';

describe('ScientificAreaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScientificAreaService = TestBed.get(ScientificAreaService);
    expect(service).toBeTruthy();
  });
});
