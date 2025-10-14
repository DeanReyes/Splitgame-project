import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuegoHistoryComponent } from './juego-history.component';

describe('JuegoHistoryComponent', () => {
  let component: JuegoHistoryComponent;
  let fixture: ComponentFixture<JuegoHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JuegoHistoryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(JuegoHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
